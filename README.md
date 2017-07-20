#QR-GEN - gerador de qr-codes

Este pequeno programa que gera qr-codes a partir de um arquivo.

O arquivo deve conter uma coluna com a informação que vai no qr-code.


## Gerar arquivo jar

```bash
$ https://github.com/fabiorecife/qrgen.git
$ cd qrgen
$ mvn clean package assembly:assembly -Dmaven.test.skip=true
$ cd target
$ java -cp qrgen-1.0.0-jar-with-dependencies.jar App -ajuda

usage: qrgen
(obs: a primeira linha é ignorada)
versão:1.0.0
 -ajuda              ajuda
 -arquivo <arg>      (obrigatório) arquivo com a amostra a ser gerada pelo
                     qr-code
 -coluna <arg>       coluna com o valor ser colocado no qr-code e nome do
                     arquivo
 -formato <arg>      formato de saida:jpg, png
 -regex <arg>        expressão regular para selecionar texto a ser
                     substituido
 -saida <arg>        (obrigatório) diretorio de saida dos arquivos
 -separador <arg>    separador usado o default é tab
 -substituir <arg>   valor que substitui a seleção da expressao regular
 -tamanho <arg>      tamanho em pixel, usar x,y, exemplo: 160,160

```


##Exemplo de arquivo de entrada

``
cpf,nome
05329160340,JOAO FAQUI
46248128081,TIAGO DAQUI 
```

##Exemplo 1 

```bash
$ java -cp qrgen-1.0.0-jar-with-dependencies.jar App -arquivo amostra.csv -saida /Users/nome/Downloads/qr-codes -separador ,  
```