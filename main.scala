
import scala.io.Source;
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Queue
import scala.collection.mutable.ArrayBuffer

object Main {

  /** Classe 'Vertex' contém todos os atributos necessários para a execução do BFS e Floyd-Warshall*/  
  class Vertex(var idVertex: Int){

    var id: Int = idVertex
    var farness: Double = 0
    var closeness: Double = 0
    var distance: Double = 0
    var parent: Vertex = null
    var listVertex: ListBuffer[Int] = ListBuffer()
    var allDistance: ArrayBuffer[Double] = ArrayBuffer()
    var path: ArrayBuffer[Int] = ArrayBuffer()
    var distancePath: ArrayBuffer[Double] = ArrayBuffer() 
  }

  class FloydWarshall{

    /** O método 'initialize' inicializa todos os componentes para a execução do algoritmo Floyd-Warshall*/
    def initialize(graph: Map[Int,Vertex]){
      for ((i, v1) <- graph) { 
        for ((j, v2) <- graph) { 
          v1.path += 0 
          v1.distancePath += Double.PositiveInfinity 
        }
      }
    }

    /** O método 'run' executa o algoritmo Floyd-Warshall a partir de um grafo*/
    def run(graph: Map[Int,Vertex]){

      initialize(graph)

      for ((k, value) <- graph) { 
        for ((i, value) <- graph) { 
          for ((j, value) <- graph) { 
            var path: Double = math.min(graph(i).allDistance(j),graph(i).allDistance(k) + graph(k).allDistance(j))

            if(path != graph(i).distancePath(j)){
            // if(path < graph(i).allDistance(j) || 
            //     (path != Double.PositiveInfinity && path != 0 &&  graph(i).distancePath(j) == Double.PositiveInfinity)){
              graph(i).path(j) = k
              graph(i).distancePath(j) = path 
            }            
          }
        }
      }
    }

    /** O método 'getPath' fornece um caminho após a execução do algoritmo Floyd-Warshall*/
    def getPath(graph: Map[Int, Vertex], idInit: Int, idDest: Int): Double = {
      return graph(idInit).allDistance(idDest)        
    }     
  }

  class BreadthFirstSearch{

    /** O método 'initialize' inicializa todos os atributos necessários para a execução do BFS*/
    def initialize(graph: Map[Int, Vertex], vertex: Vertex) {

      for ((key, value) <- graph) { 
        value.parent = null
        value.distance = Double.PositiveInfinity
        vertex.allDistance += Double.PositiveInfinity
      }

      vertex.distance = 0
      vertex.allDistance(vertex.id) = 0
    }

    /** O método 'run' executa o algoritmo BreadthFirstSearch a partir de um grafo e um vértice de início*/
    def run(graph: Map[Int, Vertex], vertex: Vertex) = {

      var queue = Queue[Vertex]()
      var all_distance: Double = 0
      initialize(graph, vertex)

      queue.enqueue(vertex)
      while(!queue.isEmpty){
        var next: Vertex = queue.dequeue()
        if(!next.listVertex.isEmpty){
          for (i <- next.listVertex){
            if(graph(i).parent == null && graph(i).distance == Double.PositiveInfinity){
              graph(i).parent = next;
              graph(i).distance = next.distance + 1.0;
              all_distance += graph(i).distance
              vertex.allDistance(graph(i).id) = graph(i).distance
              queue.enqueue(graph(i))
            }
          }
        }
      }
    }
  }

  def main(args: Array[String]) = {
    execute()
  }

  def execute(){
    var filename = "edges"
    var graph: Map[Int, Vertex] = createGraph(filename)
  
    var bfs = new BreadthFirstSearch()
    var fw = new FloydWarshall()

    for ((key, vertex) <- graph) { 
      bfs.run(graph, vertex)
    }
    fw.run(graph)

    calculateMeasures(graph)  
  }
  
  /** O método 'calculateMeasures' realiza o calcula das métricas 'farness' e 'closeness'*/ 
  def calculateMeasures(graph: Map[Int, Vertex]){
    var vertex: Vertex = null
    for ((key, vertexAux) <- graph) { 
      vertexAux.farness = 0
      for (i <- vertexAux.distancePath){
        vertexAux.farness += i
      }
      vertexAux.closeness = 1/vertexAux.farness
      if(vertex == null || vertex.closeness < vertexAux.closeness){
        vertex = vertexAux
      }
      println("ID " + vertexAux.id + " | FARNESS: " + vertexAux.farness + " | CLOSENESS: " + vertexAux.closeness)
    }
    println("\nO vértice central: ")
    println("ID: " + vertex.id)
    println("FARNESS: " + vertex.farness)
    println("CLOSENESS: " + vertex.closeness)
  }

  /** O método 'createGraph' gera um grafo a partir das arestas fornecidas no arquivo 'filename'*/
  def createGraph(filename: String) : Map[Int, Vertex] = {

    val graph: Map[Int, Vertex] = Map()
    try {
      for (line <- Source.fromFile("example/"+filename).getLines()) {
        var id_init = line.split(" ")(0).toInt
        var id_dest = line.split(" ")(1).toInt

        // Se o vértice existe no grafo
        if(!graph.contains(id_init)){
          graph += (id_init -> new Vertex(id_init))
        }
        graph(id_init).listVertex += id_dest
      }
      return graph
    } catch {
      case ex: Exception => println("Bummer, an exception happened.")
      // case ex1: FileNotFoundException => println("Couldn't find that file.")
      // case ex2: IOException => println("Had an IOException trying to read that file")
    }
    return graph
  }

  def timer[A](blockOfCode: => A) = {
      val startTime = System.nanoTime
      val result = blockOfCode
      val stopTime = System.nanoTime
      val delta = stopTime - startTime
      (result, delta/1000000d)
  }
 
}
