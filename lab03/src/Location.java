/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    // переопределяем методы equals и hashCode для корректной работы нашего класса в составе HashMap

    @Override // переопределение метода базового класса
    public boolean equals(Object obj){
        if (!(obj instanceof Location)) return false; // если объект другого класса
        if (this == obj) return true; // если ссылки равны

        // приводим объект к классу Location
        Location location = (Location)obj;

        // сравниваем значения полей
        return (this.xCoord == location.xCoord &&
                this.yCoord == location.yCoord);
    }

    @Override // переопределение метода базового класса
    public int hashCode(){
        // домножаем x на простое число, чтобы различать (a, b) и (b, a)
        // берется простое число для уменьшения количества коллизий
        return xCoord * 31 + yCoord;
    }
}