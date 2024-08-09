# Mini-guide on using Erlang for exercises


This guide outlines the steps to run the Erlang code for lectures and exercises in the course Practical Concurrent and Parallel Programming (PCPP).
In this course, we will use the programming language Erlang as a vehicular language to put in practice the concurrency concepts related to message passing.
Erlang is ideal for this purpose, as it embeds the actor model by design (which is the concurrency model that we will study in PCPP for message passing).
We will use Erlang version 26.2.3－as this is the newest version with packed installers in all major OSs. /RAUL: This sentence may change after testing in all platforms./

Unlike for Java programs, we will not use a build tool for Erlang.
Instead, we will use the built-in `make` facility in the Erlang interpreter.

Besides the material in the lectures. We recommend the online textbook [Learn You Some Erlang for great good! ](https://learnyousomeerlang.com/content) as a reference to program in Erlang. We recommend that you start looking into Erlang from the beginning of the course, so that when we start using Erlang you are already familiar with the syntax and basic functionality.
  
* You can download the Erlang installer 
  * Ubuntu (and other Linux distros) and select 26.2.3-1: https://www.erlang-solutions.com/downloads/
  * Mac OS (the page refers to brew): https://www.erlang-solutions.com/downloads/
  * Windows: https://www.erlang.org/patches/otp-26.2.3
	
  

## Install Erlang
* Ubuntu 22.04.4 (jammy)
  1. Download Erlang installer: https://www.erlang-solutions.com/downloads/
	 * Select "Erlang OTP" -> "Ubuntu" -> "VIEW ALL" -> "26.2.3-1" -> "Download" on the "Ubuntu jammy" row
  2. `$ sudo dpkg -i esl-erlang_26.2.3-1~ubuntu~jammy_amd64.deb`
	 * My computer did not have all dependencies; `apt install -f` detected that `libncurses5 libsctp1 libtinfo5` were missing. 
	   After installing them, the installation of Erlang completed successfully.
	 * `apt install -f` also suggested installing `lksctp-tools`. I installed it.
	
* W10
  1. Download Erlang installer: https://www.erlang.org/patches/otp-26.2.3
	 * Choose "Download Windows Installer" this will download a file: `otp_win64_26.2.3.exe`.
  2. Run this, a window opens (Choose components) click next. A new window pops up, make sure you note the path to the installed files. Lets denote this path `path/to/erlang`.
  3. Add this path to your windows `Path` variable the path to the `bin` folder, i.e., `path/to/erlang/bin`---see https://adoptingerlang.org/docs/development/setup/ or follow this steps:
	 1. Open "Edit the system environment variables".
	 2. Select "Environment Variables...".
	 3. Double-click in the `Path` variable.
	 4. Click "New".
	 5. Add the path to `bin` folder, i.e., `/path/to/erlang/bin` (replace `/path/to/erlang/bin` with the actual path where you installed Erlang).
* W11 - TBD
* Mac OS - TBD (possibly different versions)
  
## Running code

After successfully installing Erlang, you should be able to start the Erlang interpreter with `$ erl`. You can exit the Erlang interpreter by, e.g., typing `> q().` (note that the "." is part of the command).

To test your installation, please run the example in the folder `testing_erlang`. This folder includes 3 projects `broadcast`, `demo` and `modules`; each of them in a different folder.

1. Navigate to the directory of to the project `demo`, e.g., 

	`$ cd testing_erlang/demo`

2. Compile the code using `erl`. We will use the `make` command in `erl`:

	`$ erl -make`
	
   NOTE: This command compiles all .erl files in the directory－but not subdirectories, therefore all source code files must be in this directory. Each file in the directory corresponds to an Erlang *module* (Hereafter I use the term module to refer to the content in .erl files).
   
3. Run the project by using `erl` as follows:

	1. `$ erl` (enter the interpreter)
	2. `> demo:start().` (execute the start function in `demo` module | note that and the "." is part of the command)
	3. `> q().` (exit the interpreter)
	
	In general, if you start the interpreter in a directory with compiled .erl files (i.e., .beam files). You can directly call any exported functions `function(...)` within a module `module` by running `> module:function(...).`. As a convention, we will always use a function `start()` as the entry point for the module. This corresponds to the `public static void main(String args[])` method in a Java class.

4. Repeat steps 1–3 for the modules in the folders `broadcast` and `modules`. In the folder `modules`, the starting module is `module_a`, and, as required, it contains a `start()` function.
