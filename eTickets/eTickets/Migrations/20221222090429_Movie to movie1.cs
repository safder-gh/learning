using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace eTickets.Migrations
{
    public partial class Movietomovie1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_movie_producer_producer_id1",
                table: "movie");

            migrationBuilder.DropIndex(
                name: "IX_movie_producer_id1",
                table: "movie");

            migrationBuilder.DropColumn(
                name: "producer_id1",
                table: "movie");

            migrationBuilder.CreateIndex(
                name: "IX_movie_producer_id",
                table: "movie",
                column: "producer_id");

            migrationBuilder.AddForeignKey(
                name: "FK_movie_producer_producer_id",
                table: "movie",
                column: "producer_id",
                principalTable: "producer",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_movie_producer_producer_id",
                table: "movie");

            migrationBuilder.DropIndex(
                name: "IX_movie_producer_id",
                table: "movie");

            migrationBuilder.AddColumn<Guid>(
                name: "producer_id1",
                table: "movie",
                type: "uniqueidentifier",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_movie_producer_id1",
                table: "movie",
                column: "producer_id1");

            migrationBuilder.AddForeignKey(
                name: "FK_movie_producer_producer_id1",
                table: "movie",
                column: "producer_id1",
                principalTable: "producer",
                principalColumn: "id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
