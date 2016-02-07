# Closeness Centrality em Scala
Um algoritmo que resolve o problema de proximidade em grafos.

# Descrição do problema
A proximidade central (Closeness Centrality) de um vértice u é a distância mínima para todos os vértices pertencentes ao grafo. O objetivo é encontrar o vértice mais próximo dos restantes.

# Regras e premissas.
- A peso de cada aresta é 1.
- Digrafo fortemente conexo.

# Solução 
- Leitura do arquivo 'edge'.
- Gerada a lista de adjância com as arestas contidas no arquivo.
- Implementado o algoritmo de busca em largura (Breadth-first search) para encontrar a distância do vértice para todos os outros do grafo.
- Aplicação do algoritmo para todos os vértices.
- Calculo das medidas de farness e closeness.

# Dependência
- Java runtime, versão 1.8 ou superior
- Scala 2.11.6

# Execução
Para iniciar o algoritmo, execute: 
- scala main.scala

