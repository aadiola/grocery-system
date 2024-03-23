Step 2: run `docker compose up`

Step 3: `npx quasar dev`


Step 1: Compile java package `mvn clean package -Dskiptests` or `mvn package -DskipTests`

To cleaup database run:
`docker compose down`
`docker system prune -a`
`docker volume rm grocery-system_postgres_data `


