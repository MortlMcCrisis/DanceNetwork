import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:443",
  realm: "dance-network",
  clientId: "dance-network-admin",
});

export default keycloak;