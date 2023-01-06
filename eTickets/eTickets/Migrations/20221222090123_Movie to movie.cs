using Microsoft.EntityFrameworkCore.Migrations;

namespace eTickets.Migrations
{
    public partial class Movietomovie : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_actor_movie_Movies_MovieId",
                table: "actor_movie");

            migrationBuilder.DropForeignKey(
                name: "FK_Movies_cinema_cinema_id",
                table: "Movies");

            migrationBuilder.DropForeignKey(
                name: "FK_Movies_producer_producer_id1",
                table: "Movies");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Movies",
                table: "Movies");

            migrationBuilder.RenameTable(
                name: "Movies",
                newName: "movie");

            migrationBuilder.RenameIndex(
                name: "IX_Movies_producer_id1",
                table: "movie",
                newName: "IX_movie_producer_id1");

            migrationBuilder.RenameIndex(
                name: "IX_Movies_cinema_id",
                table: "movie",
                newName: "IX_movie_cinema_id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_movie",
                table: "movie",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "FK_actor_movie_movie_MovieId",
                table: "actor_movie",
                column: "MovieId",
                principalTable: "movie",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_movie_cinema_cinema_id",
                table: "movie",
                column: "cinema_id",
                principalTable: "cinema",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_movie_producer_producer_id1",
                table: "movie",
                column: "producer_id1",
                principalTable: "producer",
                principalColumn: "id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_actor_movie_movie_MovieId",
                table: "actor_movie");

            migrationBuilder.DropForeignKey(
                name: "FK_movie_cinema_cinema_id",
                table: "movie");

            migrationBuilder.DropForeignKey(
                name: "FK_movie_producer_producer_id1",
                table: "movie");

            migrationBuilder.DropPrimaryKey(
                name: "PK_movie",
                table: "movie");

            migrationBuilder.RenameTable(
                name: "movie",
                newName: "Movies");

            migrationBuilder.RenameIndex(
                name: "IX_movie_producer_id1",
                table: "Movies",
                newName: "IX_Movies_producer_id1");

            migrationBuilder.RenameIndex(
                name: "IX_movie_cinema_id",
                table: "Movies",
                newName: "IX_Movies_cinema_id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Movies",
                table: "Movies",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "FK_actor_movie_Movies_MovieId",
                table: "actor_movie",
                column: "MovieId",
                principalTable: "Movies",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Movies_cinema_cinema_id",
                table: "Movies",
                column: "cinema_id",
                principalTable: "cinema",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Movies_producer_producer_id1",
                table: "Movies",
                column: "producer_id1",
                principalTable: "producer",
                principalColumn: "id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
