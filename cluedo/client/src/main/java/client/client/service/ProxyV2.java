package client.client.service;

import client.client.config.CodeStatus;
import client.client.config.ServiceConfig;
import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProxyV2 implements IProxyV2 {


    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Collection<User> getAllUsers() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ServiceConfig.URL_USER))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<User> userList = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

        return userList;

    }

    @Override
    public Collection<User> getAllUsersWithFiltre(String filtre) {
        return null;
    }

    @Override
    public User connexion(String login, String pwd) throws IOException, InterruptedException, MdpIncorrectOuNonInscritException {
        HttpClient client = HttpClient.newHttpClient();
        String usr = objectMapper.writeValueAsString(new User(login,pwd));
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept","application/json")
                .uri(URI.create(ServiceConfig.URL_USER_CONNEXION))
                .header("Content-type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(usr))
                .build();


        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
      if (response.statusCode()==CodeStatus.UNAUTHORIZED.getCode()){
            throw new MdpIncorrectOuNonInscritException();
        }else if(response.statusCode()==CodeStatus.CREATED.getCode()) {

            // faut retourner l id venant du serveur ... qui est dans location ... le return est faux
            Optional<String> i = response.headers().firstValue("Location");
            String[] idd = i.get().split("/");
            String idServ = idd[idd.length - 1];
            // idéalement code status 200 aulieu de 201
            return new User(idServ, login, pwd);
        }
      throw new RuntimeException();
    }

    @Override
    public void deconnexion() {

    }

    @Override
    public User insciption(String login, String pwd) throws DejaInscritException {
        try {
            String userDTOJSON = objectMapper.writeValueAsString(new User(login,pwd));
            System.out.println(userDTOJSON);
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(ServiceConfig.URL_USER))
                    .header("Content-Type","application/json").POST(HttpRequest.BodyPublishers.ofString(userDTOJSON)).build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            if (response.statusCode() == CodeStatus.CREATED.getCode()) {
                String resourceUri = response.headers().firstValue("Location").get();
                String[] splitIt = resourceUri.split("/");
                return new User(splitIt[splitIt.length-1],login,pwd);
            }else if(response.statusCode()==CodeStatus.CONFLICT.getCode()){
                throw new DejaInscritException();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Erreur innatendue !");
    }

    @Override
    public void desinscrition() {

    }

    @Override
    public Collection<Invitation> getAllInvitationsRecues() {
        return null;
    }

    @Override
    public Collection<Invitation> getAllInvitationsEmises() {
        return null;
    }
}
