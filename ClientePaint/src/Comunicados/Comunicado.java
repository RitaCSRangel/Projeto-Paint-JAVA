package Comunicados;
import java.io.*;

/*Resumo da Classe:
- Classe de corpo vazio (Oca)
- É a classe que está na base da hierarquia.
- Ela implementa a interface serializable que é a responsável por permitir a comunicação entre os sockets de clientes e servidor.
Somente objetos de classes que herdam de serializable podem ser transmitidos pelos sockets. Serializable é uma interface com 
outras funções também, como por exemplo permitir que objetos que herdam de serializable possam ser salvos em aquivos ou em bancos
de dados. 
*/

public class Comunicado implements Serializable, Cloneable
{}
