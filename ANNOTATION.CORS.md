# CORS (Cross-Origin Resource Sharing)

O CORS (Cross-Origin Resource Sharing) é um mecanismo que permite que uma página da web acesse recursos restritos de um servidor em um domínio diferente. Em termos simples, o CORS é uma abordagem de segurança que permite que servidores indiquem a partir de quais origens (domínios, esquemas ou portas) um navegador deve permitir o carregamento de recursos. Isso é fundamental para a segurança na web, pois impede que um site malicioso acesse informações privadas de um usuário em outro site.

Para entender melhor o CORS, é essencial considerar que os navegadores restringem solicitações HTTP entre origens diferentes iniciadas por scripts, como o fetch() e o XMLHttpRequest, devido à política de mesma origem. O CORS, por sua vez, permite solicitações HTTP seguras e transferências de dados entre navegadores e servidores, utilizando novos cabeçalhos HTTP para descrever quais origens são permitidas.

Em resumo, o CORS é uma medida de segurança que controla e permite solicitações HTTP entre diferentes origens, garantindo que apenas origens autorizadas possam acessar recursos específicos de um servidor. Ele é essencial para a arquitetura de aplicativos da web, promovendo a troca segura de recursos entre diferentes origens enquanto mantém medidas de segurança necessárias.

# Classe WebMvcConfigurer

Em um aplicativo Spring Boot, o CORS pode ser configurado usando a classe WebMvcConfigurer e a interface WebMvcConfigurer.

```java
package com.apirest.projetoformulariocadastro.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/usuario/**")
                        .allowedOrigins("*") // Permit all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow common HTTP methods
                        .allowedHeaders("*"); // Permit all headers
            }
        };
    }

}
```

A classe `CorsConfig` é uma configuração do Spring Framework para habilitar o CORS (Cross-Origin Resource Sharing) em uma aplicação Spring Boot e ela é anotada com `@Configuration`, o que indica que ela é uma classe de configuração do Spring.

Dentro da classe `CorsConfig`, há um método `corsConfigurer()` anotado com `@Bean`, o que significa que ele retorna um bean que pode ser injetado em outras partes da aplicação. O método `corsConfigurer()` retorna uma instância de `WebMvcConfigurer`, que é uma interface que permite configurar o processamento de solicitações HTTP no Spring.

O método `addCorsMappings()` é sobreescrito para adicionar uma configuração de CORS para a URL `/usuario/**`. A configuração permite todas as origens (`allowedOrigins("*")`), métodos HTTP comuns (`allowedMethods("GET", "POST", "PUT", "DELETE")`), e todos os cabeçalhos (`allowedHeaders("*")`).

Em resumo, esse script é uma configuração do Spring Framework para habilitar o CORS em uma aplicação Spring Boot, permitindo que solicitações HTTP sejam feitas de diferentes origens para a URL `/usuario/**`. Isso é útil quando está desenvolvendo uma API RESTful que será consumida por aplicativos em diferentes domínios.

# HTML index.html

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Página Principal</title>
  </head>
  <body>
    <script>
      function getUser() {
        fetch('http://localhost:8080/projeto-api-rest/usuario/all')
          .then((response) => {
            if (!response.ok) {
              throw new Error("Erro ao fazer a requisição: " + response.status);
            }
            return response.json();
          })
          .then((data) => {
            console.log("Retorno da requisição:", data);
          })
          .catch((error) => {
            console.error("Ocorreu um erro: ", error);
          });
      }
      getUser();
    </script>
  </body>
</html>
```

O script acima é responsável por fazer uma solicitação HTTP GET para obter todos os usuários de um servidor RESTful com o endpoint `http://localhost:8080/projeto-api-rest/usuario/all`.

A função `getUser()` é chamada assim que o script é executado, o que faz com que a solicitação seja enviada para o servidor. A função `fetch()` é usada para fazer a solicitação e retorna uma promessa que é resolvida quando a resposta do servidor é recebida.

O primeiro `then()` é chamado quando a resposta do servidor é recebida e verifica se a resposta foi bem-sucedida usando a propriedade `ok`. Se a resposta não for bem-sucedida, uma exceção é lançada com o código de status da resposta.

Se a resposta for bem-sucedida, o segundo `then()` é chamado e a resposta é convertida em JSON usando o método `json()`. O resultado é então exibido no console usando `console.log()`.

Se ocorrer um erro durante a solicitação, o método `catch()` é chamado e o erro é exibido no console usando `console.error()`.

Em resumo, esse script JavaScript é responsável por fazer uma solicitação HTTP GET para obter todos os usuários de um servidor RESTful e exibir os resultados no console do navegador.

## Método Global fetch()

O `fetch()` é um método global em JavaScript que inicia o processo de busca de um recurso na rede, retornando uma promessa que é cumprida assim que a resposta é recebida. Ele é uma maneira moderna de fazer solicitações de rede, semelhante ao XMLHttpRequest (XHR), mas com a diferença fundamental de que o Fetch API utiliza Promises.

Ao utilizar o `fetch()`, você pode fazer solicitações de rede para obter recursos de um servidor, como dados JSON, imagens, arquivos, entre outros. O `fetch()` retorna uma promessa que pode ser encadeada com métodos como `then()` para lidar com a resposta da solicitação, permitindo que você processe os dados recebidos de forma assíncrona.

Além disso, o `fetch()` oferece flexibilidade para configurar a solicitação, permitindo especificar o método HTTP, cabeçalhos, modo de requisição, cache, entre outros parâmetros. Ele é uma alternativa mais simples e limpa ao XMLHttpRequest, evitando a complexidade dos callbacks e fornecendo uma API mais amigável para lidar com solicitações de rede.

Em resumo, o `fetch()` é uma poderosa ferramenta em JavaScript para fazer solicitações de rede e interagir com recursos externos, como APIs, de forma eficiente e assíncrona, utilizando Promises para lidar com os resultados das solicitações.

---

# Autor
## Feito por `Daniel Penelva de Andrade`