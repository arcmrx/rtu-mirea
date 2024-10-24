import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {
            // Запрос сторон треугольника
            System.out.print("Введите сторону 1: ");
            double side1 = input.nextDouble();
            System.out.print("Введите сторону 2: ");
            double side2 = input.nextDouble();
            System.out.print("Введите сторону 3: ");
            double side3 = input.nextDouble();

            // Запрос цвета
            System.out.print("Введите цвет треугольника: ");
            String color = input.next();

            // Запрос заполненности
            System.out.print("Треугольник закрашен? (true/false): ");
            boolean isFilled = input.nextBoolean();

            // Попытка создания объекта Triangle
            Triangle triangle = new Triangle(side1, side2, side3);
            triangle.setColor(color);
            triangle.setFilled(isFilled);

            // Вывод информации о треугольнике
            System.out.println("\nИнформация о треугольнике:");
            System.out.println(triangle.toString());
            System.out.println("Площадь: " + triangle.getArea());
            System.out.println("Периметр: " + triangle.getPerimeter());
            System.out.println("Цвет: " + triangle.getColor());
            System.out.println("Закрашен: " + triangle.isFilled());
        } catch (IllegalTriangleException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            // Закрываем Scanner
            input.close();
        }
    }
}
