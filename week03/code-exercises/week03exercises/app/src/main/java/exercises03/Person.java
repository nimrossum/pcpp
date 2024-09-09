package exercises03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Person {

    private static long globalID = 0;
    private static final Object globalIdLock = new Object();
    private static boolean firstPerson = true;
    private final long id;
    private volatile String name;
    private volatile int zip;
    private volatile String address;

    public Person(long customGlobalId){
        synchronized (globalIdLock) {
            if(firstPerson){
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
}
