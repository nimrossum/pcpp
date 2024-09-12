package exercises03;

import java.util.concurrent.Delayed;

public class Person {

    private static long globalID = 0;
    private static final Object globalIdLock = new Object();
    private static boolean firstPerson = true;
    private final long id;
    private volatile String name;
    private volatile int zip;
    private volatile String address;

    public Person(long customGlobalId) {
        synchronized (globalIdLock) {
            if (firstPerson) {
                globalID = customGlobalId;
            }
            this.id = globalID;
            globalID++;
            firstPerson = false;
        }
    }

    public Person(){
        this(0);
    }

    public synchronized void changeAddressAndZip(String newAddress, int newZip){
        address = newAddress;
        zip = newZip;
    }

    public long getId(){ // id is static so no need for it to be synchronized
        return id;
    }

    public synchronized String getName(){
        return name;
    }

    public synchronized int getZip(){
        return zip;
    }

    public synchronized String getAddress(){
        return address;
    }

    public synchronized void setName(String newName){
        name = newName;
    }

    // 3.2.3
    public static void main(String[] args) {
        Runnable createPeople = () -> {
            for (int i = 0; i < 50; i++) {
                Person person = new Person();
                person.setName("Person " + person.getId());
                person.changeAddressAndZip( "Some Address " + person.getId(), 12345);
                System.out.println("Created " + person.getName() + " with ID: " + person.getId());
            }
        };

        // Create several threads to create and use Person instances
        Thread t1 = new Thread(createPeople);
        Thread t2 = new Thread(createPeople);
        Thread t3 = new Thread(createPeople);

        t1.start(); t2.start(); t3.start();

        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException ignored) {}
    }
}
