Item2CollectionRuller
=====================

### Sumário

Objetivo
Requisitos de uso
Uso
Utilização via interface gráfica
Utilização via console
Observações
Modelo de Dados
XML
CSV
Referências


### Objetivo

O Item2CollectionRuler permite a vinculação de um item a uma ou mais coleções no DSpace a partir do processamento dos valores encontrados em um metadado específico.


### Requisitos de uso

Sistemas operacionais: Windows Vista ou superior, Linux, Mac OS X.
Java Runtime: Oracle ou OpenJDK versão 7 ou superior.

### Uso

Existem 2 modos de utilizar a aplicação uma via interface gráfica e outra via console do sistema.

#### Utilização via interface gráfica

Para a utilização via interface gráfica, é necessário preencher os campos:
* rules.xml: caminho do arquivo XML contendo as regras para a vinculação; o arquivo XML deve seguir as especificações da seção Modelo de Dados -> XML.
* in.csv: caminho do arquivo CSV contendo os metadados dos itens; o arquivo CSV deve serguir as especificações da seção Modelo de Dados -> CSV.
* out.csv: caminho e nome do arquivo CSV que deverá ser criado após a execução do Item2CollectionRuler.
* csv column: cabeçalho da coluna do arquivo CSV (in.csv) que contem os valores que servirão de base para a vinculação; o cabeçalho informado deve conter a indicação do idioma entre colchetes (exemplos: [en], [pt], [es]), se tal indicação constar no CSV.
* default: handle da coleção a qual deverão ser vinculados os itens que não puderem ser vinculados a nenhuma das coleções especificadas no rules.xml.

Exemplo:
* rules.xml: D:\data\departments.xml
* in.csv: D:\data\items-to-be-mapped.csv
* out.csv: D:\data\items-mapped.csv
* csv column: dc.description.affiliation[en]
* default: university/987

#### Utilização via console

Existem 6 parâmetros na utilização via console e uma vez que algum desses informados o programa não executará a interface gráfica, são eles:
* -h ou --help:[Opcional]  Mostra os parametros possíveis a serem utilizados.
* -H ou --handle (valor):[Opcional] Informar o handle da coleção padrão para quando não se encaixar em nenhuma regra. Valor padrão: 12345678/1
* -c ou --column (valor):[Opcional] Informar o titulo da coluna onde o programa deve analisar os valores. Valor padrão: dc.description.affiliation[].
* -i ou --input-file (valor):[Obrigatório] Informar o caminho do arquivo CSV contendo os metadados dos itens; o arquivo CSV deve serguir as especificações da seção Modelo de Dados -> XML.
* -o ou --output-file (valor):[Obrigatório] Informar o caminho e nome do arquivo CSV que deverá ser criado após a execução do Item2CollectionRuler.
* -m ou --map-file (valor):[Obrigatório] Informar o caminho do arquivo XML contendo as regras para a vinculação; o arquivo XML deve seguir as especificações da seção Modelo de Dados -> CSV.

#### Observações

A execução do programa leva em torno de alguns segundos, caso a execução esteja demorando recomenda-se a modificação do valor padrão da memória do java pelo parâmetro: -Xmx???m onde ??? é o número de megabytes de memória a disponibilizar.  
Modelo de Dados

#### XML

1. Para a criação de regras é necessário utilizar um arquivo XML com as seguintes especificações:
Versão 1.0
2. Utilizar codificação UTF-8
3. Deve seguir exclusivamente o schema Item2CollectionRuller em http://base.repositorio.unesp.br/XMLSchema/Item2CollectionRuler

#### CSV

O Item2CollectionRuler utiliza o mesmo padrão de aquivo CSV (RFC4180) que o software DSpace.

### Referências

* http://www.ietf.org/rfc/rfc4180.txt
* https://wiki.duraspace.org/display/DSDOC4x/Batch+Metadata+Editing#BatchMetadataEditing-TheCSVFiles
* http://base.repositorio.unesp.br/XMLSchema/Item2CollectionRuler
* https://github.com/vitorsilverio/Item2CollectionRuller
* http://www.dspace.org
