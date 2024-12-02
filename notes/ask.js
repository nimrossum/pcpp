import Bun, { env } from "bun";
import { OpenAI } from "openai";

const course = "Practical Concurrent and Parallel Programming"

const ai = new OpenAI({
  apiKey: env.OPENAI_API_KEY,
});

// const data = await Bun.file("./questions_empty.md").text();
const data = await Bun.file("./exam.md").text();

const questions = data
  .split("\n")
  .filter((line) => line.startsWith("##"))

const questionAmount = questions.length
// const questionAmount = Math.max(10, questions.length);

const shuffledQuestions = questions
  .sort(() => Math.random() - 0.5)
  .slice(0, questionAmount);

let i = 0;

function askRandomQuestion() {
  if (i === shuffledQuestions.length) {
    console.log("No more questions");
    return null;
  }
  const question = shuffledQuestions[i];
  i++;
  if (!question) {
    console.log(
      "No more questions. Press enter to list questions to study later."
    );
    return null;
  }
  const prefix = `[${i}/${shuffledQuestions.length}]`;
  console.log(`${prefix} ${question}`);
  process.stdout.write(`> `);
  return question;
}

console.clear();

const questionsToStudy = [];

let question = askRandomQuestion();
let total_score = 0;

require("readline")
  .createInterface({ input: process.stdin })
  .on("line", async (data) => {
    // Read what the user wrote
    if (data === "quit" || question === null) {
      console.log(
        `Total score: ${total_score}/${questionAmount * 10}`
      );
      console.log();
      console.log("Questions to study later:");
      console.log(
        questionsToStudy
          .map((question, index) => `- [${index + 1}] ${question}`)
          .join("\n")
      );
      process.exit();
    }
    if (data === "") {
      questionsToStudy.push(question);
    } else if (data === "hint") {
      const result = await ai.chat.completions.create({
        model: "gpt-4o",
        messages: [
          {
            role: "system",
            content: `Provide a few comma separated hint keywords for the following question: ${question}`,
          },
          { role: "user", content: question },
        ],
      });
      console.log(`✨ ${result.choices[0].message.content}`);
      console.log();
      process.stdout.write(`> `);

      return;
    } else if (data === "ai" || data === "skip") {
      questionsToStudy.push(question);
      await answerQuestion(question);
    } else {
      // Verify the answer with AI
      const answer = data

      const prompt = `You are an examinator in the course ${course}.
You must verify the answer to the following question. The student is asked to briefly explain their answer in 2-3 sentences.
Start your sentence with 'Yes' if the answer is correct.
Do not use markdown syntax, only plain text.
Finish your answer with a "Score: X/10." where X is a number between 0 and 10.
The question that the user answered was:
${question}. The user answered: ${answer}`;
      const result = await ai.chat.completions.create({
        model: "gpt-4o",
        messages: [
          {
            role: "system",
            content: prompt,
          },
        ],
      });

      const score = parseInt(
        result.choices[0].message.content?.match(/Score: (\d+)/)?.[1] ?? "0"
      );
      if (!result.choices[0].message.content?.startsWith("Yes") || score < 7) {
        questionsToStudy.push(question);
      }
      total_score += score;
      console.log(`✨ ${result.choices[0].message.content}`);
      console.log();
    }
    await answerQuestion(question);

    question = askRandomQuestion();


  });

async function answerQuestion(question) {
  const result = await ai.chat.completions.create({
    model: "gpt-4o",
    messages: [
      {
        role: "system",
        content: `You are at an exam in the course ${course}. You must answer the following question in 2-3 sentences. Do not use markdown syntax, only plain text.`,
      },
      { role: "user", content: question },
    ],
  });
  console.log(`✨ ${result.choices[0].message.content}`);
  console.log();
}

