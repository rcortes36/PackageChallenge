The Package Challenge

I was instructed to use code that can be used for production purposes. 
For that reason:(1) I chose to use BigDecimal for Cost as is the type of number that is used money and also for weight to have precision. This is an example of how bad Double and Float can perform for calculations
public class DoubleForCurrency {
    public static void main(String[] args) {
        double total = 0.2;
        for (int i = 0; i < 100; i++) {
            total += 0.2;
        }
        System.out.println("total = " + total);        //prints total = 20.19999999999996
    }
}

(2) I chose to write a class for verifications and avoid that responsibility in specific classes. I was in two minds, but finally, I prefer this option to have a place when all the restriction were placed and can be easy to maintain
(3) I do not use extensively JavaCode. My experience has shown that JavaDoc and the generated HTML are not used unless your artifact is widely spread and used in a lot of projects and the effort to complete it is not worth it. Despite the previous, I wrote a lot of comments inside the code for me or for others that want to read it and understand it easily. 
(4) I preferred short classes with few methods than larger classes with unrelated responsibilities. The responsibility of the class can be inferred from the name or a short comment was added if it was considered necessary
(5) No special code consideration was taken to be used in a multithreading environment. the entry point or method Packer.pack is static and there are no variables which value can be in a race condition
(6) I changed the  provided pom.xml file using other plugins than made easier my development and configuring portability 
(7) I used Junit and Hamcrest because of the Collection facilities of Hamcrest and JUnit as the general unit testing framework 
(8) The project can be easily tested using mvn test 
(9) Goals test, compile, and the package was tested and can be used 
(10) The lombok framework was used to improve readability of the code