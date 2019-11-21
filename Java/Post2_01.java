public class Post2_01 {

    public static void main(String[] args) {
        Animal dog = new Animal("Coco", "Male");
        Animal cat = new Animal("Momo", "Female");

        System.out.println("==========================================");
        System.out.println("Dog and Cat");
        System.out.println("==========================================");

        System.out.println("Dog's name is " + dog.name);
        System.out.println("Dog's sex is " + dog.sex);
        System.out.println("Cat's name is " + cat.name);
        System.out.println("Cat's sex is " + cat.sex);

        System.out.println("\n==========================================");
        System.out.println("Call By Value");
        System.out.println("==========================================");

        callByValue1(dog);
        callByValue2(cat);

        System.out.println("Dog's name is " + dog.name);
        System.out.println("Dog's sex is " + dog.sex);
        System.out.println("Cat's name is " + cat.name);
        System.out.println("Cat's sex is " + cat.sex);
    }

    private static void callByValue1(Animal animal) {
        animal.name = "Happy";
        animal.sex = "Female";
    }

    private static void callByValue2(Animal animal) {
        animal = new Animal("Teddy", "Male");
    }
}

class Animal {
    String name, sex;

    Animal(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
