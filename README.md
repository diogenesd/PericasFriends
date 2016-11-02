# PericasFriends
#Software Cliente de Troca de Mensagens

#Objetivo
Familiarizar-se com o uso de sockets TCP e UDP no desenvolvimento de softwares de rede através da implementação de um cliente de
software de Troca de Mensagens, utilizando a plataforma android

#Requisitos do sistema
O cliente de Troca de Mensagens deve se comunicar com o servidor <seu.servidor.com> de
mensagens na forma de strings ASCII, terminadas pelo fim de linha padrão IETF.

#Este projeto cliente de Troca de Mensagens já está implementando as seguintes funcionalidades: 

#1. Lista de usuários e keepalive: 
Obter do servidor, estabelecendo uma conexão a cada 6 segundos, a lista de usuários
conectados através da seguinte requisição TCP <porta liberada do servidor para conexão TCP>: 
  Formatação da mensagem para troca:
      Requisição: “GET USERS <userid>:<passwd>” 
      Onde ler-se,
        <userid>: número que identifica o usuário cliente
        <passwd>: senha do usuário cliente 
      Resposta: “<userid>:<username>:<wins>:”
      Onde ler-se,
        <userid>: número que identifica o usuário
        <username>: nome do usuário
        <wins>: <Ainda não implementado essa funcionalidade no servidor>
      Exemplo.: “2756:João da Silva:4:1235:José da Silva:0:1243:Manuel da Silva:2:”

#2. Requisição de mensagens: 
Obter do servidor uma mensagem (a mais antiga) destinada ao usuário através da seguinte
requisição TCP <porta liberada do servidor para conexão TCP>:
 Formatação da mensagem para troca:
    Requisição: “GET MESSAGE <userid>:<passwd>”
    Onde ler-se,
      <userid>: número que identifica o usuário cliente
      <passwd>: senha do usuário cliente 
    Exemplo.: “GET MESSAGE 4123: rsybt” 
    Resposta: “<userid>:<msg>” 
      <userid>: número que identifica o remetente (0 significa mensagem do servidor)
      <msg>: mensagem recebida
      Obs.: se não houver mensagem é enviado “:”
    Exemplo.:“3825:Oi!” 

#3. Envio de mensagens: 
enviar ao servidor uma mensagem destinada a um usuário, ou a todos, através de uma mensagem
UDP <porta liberada do servidor para conexão UDP>:
  Formatação da mensagem para troca:
    Requisição: “SEND MESSAGE <userid1>:<passwd1>:<userid2>:<msg>” 
    Onde ler-se,
      <userid1>: número que identifica o usuário cliente
      <passwd1>: senha do usuário cliente
      <userid2>: número que identifica o destinatário (0 significa todos os usuários)
      <msg>: mensagem enviada 
    Resposta: <Ainda não implementado no servidor o retorno via UDP>
    Exemplo.: “SEND MESSAGE 3825: rsybt:1416:Hello world!” 



