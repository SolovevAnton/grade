## Настройка различных бинов

Напишите простое консольное приложения используя XML конфигурацию.

1. Создайте бин BeanA, используя DI через сеттеры, используйте property чтобы задать какое-нибудь значение поля из
   пропертей.
2. Создайте BeanB, используя DI через конструктор (BeanA должен быть аргументом конструктора).
3. Создайте BeanC со скоупом singleton и BeanD со скоупом prototype. Добавьте BeanD как поле бина BeanC. Обратите
   внимание, что бины имеют разные скоупы. Рассмотрите Lookup Method Injection.
4. Создайте BeanE и замените логику реализации одного из его методов с помощью Method Replacement.
5. Создайте BeanF и залогируйте различные этапы жизненного цикла бина.