# Closeness Centrality em Scala

# Descrição do problema
A proximidade central (Closeness Centrality) de um vértice u é a distância mínima para todos os vértices pertencentes ao grafo. O objetivo é encontrar o vértice mais próximo dos restantes.

# Regras e premissas.
- A peso de cada aresta é 1.
- Digrafo fortemente conexo.

# Tecnologias
- Algoritmo de Busca em Largura: O algoritmo realiza uma busca no grafo a partir de um vértice raiz e explora todos os vértices visitando seus vizinhos e encontrando as distâncias entre elas. No problema, utilizamos para encontrar os pesos das arestas no grafo gerado.
- Algoritmo de Floyd-Warshall: O algoritmo resolve problema do caminho mais curto entre todos os vértices do grafo orientado. No problema, aplicamos para resolver o problema de caminho mínimo.

# Solução 
- Leitura do arquivo 'edge'.
- Gerada a lista de adjância com as arestas contidas no arquivo.
- Implementado o algoritmo de busca em largura (Breadth-first search) para encontrar a distância do vértice para todos os outros do grafo.
- Implementado o algoritmo de Floyd-Warshall para resolver o problema de caminho mínimo.
- Aplicado o algoritmo de busca em largura para todos os vértices.
- Após o resultado o BFS aplica-se o grafo no algoritmo Floyd-Warshall.
- Com os pesos 'otimizados' é calculado as medidas de farness e closeness.

# Dependência
- Java runtime, versão 1.8 ou superior
- Scala 2.11.6

# Execução
Para iniciar o algoritmo, execute: 
- scala main.scala

