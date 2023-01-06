using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace eTickets.Migrations
{
    public partial class Initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "actor",
                columns: table => new
                {
                    id = table.Column<Guid>(nullable: false),
                    added_on = table.Column<DateTime>(nullable: false),
                    modified_on = table.Column<DateTime>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    profile_picture_url = table.Column<string>(nullable: true),
                    first_name = table.Column<string>(nullable: true),
                    middle_name = table.Column<string>(nullable: true),
                    last_name = table.Column<string>(nullable: true),
                    bio = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_actor", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "cinema",
                columns: table => new
                {
                    id = table.Column<Guid>(nullable: false),
                    added_on = table.Column<DateTime>(nullable: false),
                    modified_on = table.Column<DateTime>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    logo = table.Column<string>(nullable: true),
                    name = table.Column<string>(nullable: true),
                    description = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_cinema", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "producer",
                columns: table => new
                {
                    id = table.Column<Guid>(nullable: false),
                    added_on = table.Column<DateTime>(nullable: false),
                    modified_on = table.Column<DateTime>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    profile_picture_url = table.Column<string>(nullable: true),
                    first_name = table.Column<string>(nullable: true),
                    middle_name = table.Column<string>(nullable: true),
                    last_name = table.Column<string>(nullable: true),
                    bio = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_producer", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "Movies",
                columns: table => new
                {
                    id = table.Column<Guid>(nullable: false),
                    added_on = table.Column<DateTime>(nullable: false),
                    modified_on = table.Column<DateTime>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    name = table.Column<string>(nullable: true),
                    description = table.Column<string>(nullable: true),
                    price = table.Column<double>(nullable: false),
                    image_url = table.Column<string>(nullable: true),
                    start_date = table.Column<DateTime>(nullable: false),
                    end_date = table.Column<DateTime>(nullable: false),
                    movie_category = table.Column<int>(nullable: false),
                    cinema_id = table.Column<Guid>(nullable: false),
                    producer_id = table.Column<Guid>(nullable: false),
                    producer_id1 = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Movies", x => x.id);
                    table.ForeignKey(
                        name: "FK_Movies_cinema_cinema_id",
                        column: x => x.cinema_id,
                        principalTable: "cinema",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Movies_producer_producer_id1",
                        column: x => x.producer_id1,
                        principalTable: "producer",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "actor_movie",
                columns: table => new
                {
                    MovieId = table.Column<Guid>(nullable: false),
                    ActorId = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_actor_movie", x => new { x.ActorId, x.MovieId });
                    table.ForeignKey(
                        name: "FK_actor_movie_actor_ActorId",
                        column: x => x.ActorId,
                        principalTable: "actor",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_actor_movie_Movies_MovieId",
                        column: x => x.MovieId,
                        principalTable: "Movies",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_actor_movie_MovieId",
                table: "actor_movie",
                column: "MovieId");

            migrationBuilder.CreateIndex(
                name: "IX_Movies_cinema_id",
                table: "Movies",
                column: "cinema_id");

            migrationBuilder.CreateIndex(
                name: "IX_Movies_producer_id1",
                table: "Movies",
                column: "producer_id1");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "actor_movie");

            migrationBuilder.DropTable(
                name: "actor");

            migrationBuilder.DropTable(
                name: "Movies");

            migrationBuilder.DropTable(
                name: "cinema");

            migrationBuilder.DropTable(
                name: "producer");
        }
    }
}
