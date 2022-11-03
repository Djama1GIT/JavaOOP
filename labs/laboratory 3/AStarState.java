import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
  /** This is a reference to the map that the A* algorithm is navigating. **/
  private Map2D map;

  private HashMap<Location, Waypoint> openWaypoints = new HashMap<Location, Waypoint>();

  private HashMap<Location, Waypoint> closeWaypoints = new HashMap<Location, Waypoint>();


  /**
   * Initialize a new state object for the A* pathfinding algorithm to use.
   **/
  public AStarState(Map2D map)
  {
    if (map == null)
      throw new NullPointerException("map cannot be null");

    this.map = map;
  }

  /** Returns the map that the A* pathfinder is navigating. **/
  public Map2D getMap()
  {
    return map;
  }

  /**
   * This method scans through all open waypoints, and returns the waypoint
   * with the minimum total cost.  If there are no open waypoints, this method
   * returns <code>null</code>.
   **/
  public Waypoint getMinOpenWaypoint() {
    /* Если в "открытом" наборе нет вершин, функция возвращает NULL.
    Эта функция должна проверить все вершины в наборе открытых вершин,
    и после этого она должна вернуть ссылку на вершину с наименьшей общей
    стоимостью */
    var minCost = Float.MAX_VALUE;
    Waypoint minWaypoint = null;

    for (var minW : openWaypoints.entrySet()) {
      if (minW.getValue().getTotalCost() < minCost) {
        minWaypoint = minW.getValue();
        minCost = minWaypoint.getTotalCost();
      }
    }

    return minWaypoint;
  }

  /**
   * This method adds a waypoint to (or potentially updates a waypoint already
   * in) the "open waypoints" collection.  If there is not already an open
   * waypoint at the new waypoint's location then the new waypoint is simply
   * added to the collection.  However, if there is already a waypoint at the
   * new waypoint's location, the new waypoint replaces the old one <em>only
   * if</em> the new waypoint's "previous cost" value is less than the current
   * waypoint's "previous cost" value.
   **/
  /*
  Если в наборе «открытых вершин» в настоящее время нет вершины
  для данного местоположения, то необходимо просто добавить новую вершину.
   Если в наборе «открытых вершин» уже есть вершина для этой
  локации, добавьте новую вершину только в том случае, если стоимость пути до
  новой вершины меньше стоимости пути до текущей. (Убедитесь, что
  используете не общую стоимость.) Другими словами, если путь через новую
  вершину короче, чем путь через текущую вершину, замените текущую вершину
  на новую
 */
  public boolean addOpenWaypoint(Waypoint newWP)
  {
    if (openWaypoints.get(newWP.getLocation()) == null) {
      openWaypoints.put(newWP.getLocation(), newWP);
      return false;
    } else {
      if (openWaypoints.get(newWP.getLocation()).getPreviousCost() > newWP.getPreviousCost()) {
        openWaypoints.put(newWP.getLocation(), newWP);
        return true;
      }
    }

    return false;
  }


  /** Returns the current number of open waypoints. **/
  public int numOpenWaypoints() /* Этот метод возвращает количество точек в наборе открытых вершин. */
  {
    return openWaypoints.size();
  }


  /**
   * This method moves the waypoint at the specified location from the
   * open list to the closed list.
   **/
  /*
    Эта функция перемещает вершину из набора «открытых вершин» в набор
    «закрытых вершин». Так как вершины обозначены местоположением, метод
    принимает местоположение вершины.
    Процесс должен быть простым:
     Удалите вершину, соответствующую указанному местоположению
    из набора «открытых вершин».
     Добавьте вершину, которую вы удалили, в набор закрытых вершин.
    Ключом должно являться местоположение точки.*/
  public void closeWaypoint(Location loc)
  {
    closeWaypoints.put(loc, openWaypoints.remove(loc));
  }

  /**
   * Returns true if the collection of closed waypoints contains a waypoint
   * for the specified location.
   **/

  /*
  Эта функция должна возвращать значение true, если указанное
  местоположение встречается в наборе закрытых вершин, и false в противном
  случае. Так как закрытые вершины хранятся в хэш-карте с расположениями в
  качестве ключевых значений, данный метод достаточно просто в реализации.
  */
  public boolean isLocationClosed(Location loc)
  {
    return closeWaypoints.containsKey(loc);
  }
}
