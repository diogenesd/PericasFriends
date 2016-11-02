# PericasFriends
#Software Cliente de Troca de Mensagens

##Objetivo
Familiarizar-se com o uso de sockets TCP e UDP no desenvolvimento de softwares de rede através da implementação de um cliente de
software de Troca de Mensagens, utilizando a plataforma android

##Requisitos do sistema
O cliente de Troca de Mensagens deve se comunicar com o servidor <seu.servidor.com> de
mensagens na forma de strings ASCII, terminadas pelo fim de linha padrão IETF.

###Este projeto cliente de Troca de Mensagens já está implementando as seguintes funcionalidades: 

####1. Lista de usuários e keepalive: 
Obter do servidor, estabelecendo uma conexão a cada 6 segundos, a lista de usuários<br/>
conectados através da seguinte requisição TCP ```<porta liberada do servidor para conexão TCP>:``` <br/>
  Formatação da mensagem para troca:<br/>
      **Requisição:** “GET USERS ```<userid>:<passwd>” ```<br/>
      &nbsp;&nbsp;&nbsp;Onde ler-se,<br/>
                        ```<userid>: número que identifica o usuário cliente\n
                        <passwd>: senha do usuário cliente```<br/>
      &nbsp;&nbsp;&nbsp;**Resposta**: ```“<userid>:<username>:<wins>:”```<br/>
      &nbsp;&nbsp;&nbsp;Onde ler-se,<br/>
                        ```<userid>: número que identifica o usuário```<br/>
                        ```<username>: nome do usuário```<br/>
                        ```<wins>: <Ainda não implementado essa funcionalidade no servidor>```<br/>
      &nbsp;&nbsp;&nbsp;**Exemplo.:**```“2756:João da Silva:4:1235:José da Silva:0:1243:Manuel da Silva:2:”```<br/>

####2. Requisição de mensagens: 
Obter do servidor uma mensagem (a mais antiga) destinada ao usuário através da seguinte<br/>
requisição TCP <porta liberada do servidor para conexão TCP>:<br/>
 Formatação da mensagem para troca:<br/>
    Requisição: “GET MESSAGE <userid>:<passwd>”<br/>
    Onde ler-se,<br/>
      <userid>: número que identifica o usuário cliente<br/>
      <passwd>: senha do usuário cliente <br/>
    Exemplo.: “GET MESSAGE 4123: rsybt” <br/>
    Resposta: “<userid>:<msg>”<br/>
      <userid>: número que identifica o remetente (0 significa mensagem do servidor)<br/>
      <msg>: mensagem recebida<br/>
      Obs.: se não houver mensagem é enviado “:”<br/>
    Exemplo.:“3825:Oi!” <br/>

####3. Envio de mensagens: 
Enviar ao servidor uma mensagem destinada a um usuário, ou a todos, através de uma mensagem<br/>
UDP <porta liberada do servidor para conexão UDP>:<br/>
  Formatação da mensagem para troca:<br/>
    Requisição: “SEND MESSAGE <userid1>:<passwd1>:<userid2>:<msg>” <br/>
    Onde ler-se,<br/>
      <userid1>: número que identifica o usuário cliente<br/>
      <passwd1>: senha do usuário cliente<br/>
      <userid2>: número que identifica o destinatário (0 significa todos os usuários)<br/>
      <msg>: mensagem enviada <br/>
    Resposta: <Ainda não implementado no servidor o retorno via UDP><br/>
    Exemplo.: “SEND MESSAGE 3825: rsybt:1416:Hello world!” <br/>



