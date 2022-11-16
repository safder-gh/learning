using Microsoft.EntityFrameworkCore.Migrations;

namespace EmployeePortal.Migrations
{
    public partial class BuilderDesignPattern : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "computer_details",
                table: "employee",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AddColumn<string>(
                name: "system_configuration_details",
                table: "employee",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "system_configuration_details",
                table: "employee");

            migrationBuilder.AlterColumn<string>(
                name: "computer_details",
                table: "employee",
                type: "nvarchar(max)",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);
        }
    }
}
