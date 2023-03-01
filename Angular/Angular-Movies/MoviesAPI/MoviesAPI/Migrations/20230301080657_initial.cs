using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MoviesAPI.Migrations
{
    public partial class initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "genre",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    added_on = table.Column<DateTime>(nullable: false),
                    updated_on = table.Column<DateTime>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_genre", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "genre");
        }
    }
}
