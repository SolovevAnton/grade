Необходимо написать класс обертку для ArrayList, который будет работать следующим образом:

List должен хранить параметризованные значения;
Добавить 2 функции:
map() – проходит по всем элементам массива и применяет к каждому элементу определенную функцию. Должна возвращать новый
List.

Пример работы:

List<Integer>(1,2,3,4).map(x => x*2) = List<Integer>(2,4,6,8)
List<Integer>(1,2,3,4).map(x => x.toString()) = List<String>(“2”, “4”, “6”, “8”)
reduce() – производит свёртку списка в одно результирующее значение, на основе заданной комбинирующей функции.

Пример:

List(1,2,3,4).reduce((x, y) => x+y) = ((1+2)+3)+4) = 1