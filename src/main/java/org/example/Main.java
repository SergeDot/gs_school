package org.example;

public class Main {
    public static void main(String[] args) {
        //TODO: Person @Data
        Person person = new Person();
        System.out.println(person);
        System.out.println();

        System.out.println("before " + person.getAge());
        person.setAge(25);
        System.out.println("after " + person.getAge());
        System.out.println();

        System.out.println("before " + person.getName());
        person.setName("Kevin");
        System.out.println("after " + person.getName());
        System.out.println();

        System.out.println("before " + person.getEmail());
        person.setEmail("kmitnick@fido.usenet.com");
        System.out.println("after " + person.getEmail());
        System.out.println();

        //TODO: Car @RequiredArgsConstructor
//        Car car = new Car(); // will not compile without all arguments
//        Car car = new Car("Honda"); // will not compile without all arguments
        Car car = new Car("Honda", "Civic");
        System.out.println(car);
        System.out.println(car.getBrand());
        System.out.println(car.getModel());
        System.out.println();

        //TODO: Book @AllArgsConstructor
//        Book book = new Book(); // will not compile without all arguments
//        Book book = new Book("1984", "Orwell"); // will not compile without all arguments
//        Book book = new Book("1984", "Orwell", "400"); // will not compile without all arguments
        Book book = new Book("1984", "Orwell", "400", "3.99");
        System.out.println(book);
        System.out.println();

        //TODO: House @Builder @Singular
        House house = House.builder()
                .address("3434 Main St, Burg, ST, 11111")
                .room("Living")
                .room("Bathroom")
                .room("Bedroom")
                .area("1000")
                .price("100,000")
                .build();
        System.out.println(house);
        System.out.println();

        //TODO: Product @Setter/@Getter
        Product product = new Product();
        product.setType("Workstation");
        product.setBrand("Asus");
        product.setModel("ROG"); // public
        product.setCpuModel("Ultra 9"); // protected
        product.setCpuSpeed(2.8); // package
//        product.setCpuCoreNumber(); // not accessible

        //TODO: Configuration @Accessors(chain = true)
        Configuration configuration = new Configuration()
                .setPerson(person)
                .setHouse(house)
                .setCar(car)
                .setProduct(product)
                .setBook(book);
    }
}
