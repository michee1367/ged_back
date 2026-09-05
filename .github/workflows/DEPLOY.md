## Les Secrets à configurer dans GitHub

`Vous n'avez besoin que de 3 secrets dans GitHub (Settings > Secrets and variables > Actions) :`

- SERVER_HOST : L'adresse IP de votre VPS.

- SERVER_USER : L'utilisateur SSH (ex: ubuntu ou root).

- SSH_PRIVATE_KEY : La clé privée SSH autorisée sur le VPS.

## Generer clé

```bash
mkdir -p ./cles_ssh
ssh-keygen -t ed25519 -f ./cles_ssh/github_deploy_key -C "github-actions-deploy"
```
## Comment installer votre clé publique sur le serveur VPS

```bash
# Crée le dossier .ssh s'il n'existe pas encore
mkdir -p ~/.ssh

# Modifie les permissions pour des raisons de sécurité
chmod 700 ~/.ssh

# Ouvre le fichier authorized_keys avec l'éditeur nano
nano ~/.ssh/authorized_keys

```
