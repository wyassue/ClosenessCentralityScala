import scala.io.Source;
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Queue

object HelloWorld {
	
	class Vertex(var idVertex: String){
		var id: String = idVertex
		var farness: Double = 0
		var closeness: Double = 0
		var distance: Double = 0
		var parent: Vertex = null
		var listVertex: ListBuffer[String] = ListBuffer()
	}

	class BreadthFirstSearch{

		def initialize(graph: Map[String,Vertex]) {
			for ((key, value) <- graph) { 
				value.parent = null
				value.distance = Double.PositiveInfinity
			}
		}

		def run(graph: Map[String,Vertex], vertex: Vertex): Double = {

			var queue = Queue[Vertex]()
			var all_distance: Double = 0

			initialize(graph)

			vertex.distance = 0
			queue.enqueue(vertex)
			while(!queue.isEmpty){
				var next: Vertex = queue.dequeue()
				if(!next.listVertex.isEmpty){
					for (i <- next.listVertex){
						if(graph(i).parent == null && graph(i).distance == Double.PositiveInfinity){
							graph(i).parent = next;
							graph(i).distance = next.distance + 1.0;
							all_distance += graph(i).distance
							queue.enqueue(graph(i))
						}
					}
				}
			}
			return all_distance
		}
	}


  	def main(args: Array[String]) = {

		var filename = "edges"
		var graph: Map[String, Vertex] = createGraph(filename)
		// var bfs:Map[String,Vertex] = new BreadthFirstSearch().run(graph,graph("0"))		
		var bfs = new BreadthFirstSearch()

		for ((key, vertex) <- graph) { 
			var all_distance: Double = bfs.run(graph, vertex)
			vertex.farness = all_distance
			vertex.closeness = 1/ all_distance
			println(vertex.id + "|" + vertex.farness + "|" + vertex.closeness)
		}
	}

	def createGraph(filename: String) : Map[String, Vertex] = {

		val graph: Map[String, Vertex] = Map()
		try {
		  	for (line <- Source.fromFile("example/"+filename).getLines()) {
				var id_init = line.split(" ")(0)
				var id_dest = line.split(" ")(1)

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

	def varType[T](v: T) = v match {
		case _: Int    => "Int"
		case _: String => "String"
		case _         => "Unknown"
	}

  	def timer[A](blockOfCode: => A) = {
        val startTime = System.nanoTime
        val result = blockOfCode
        val stopTime = System.nanoTime
        val delta = stopTime - startTime
        (result, delta/1000000d)
    }
 
}

