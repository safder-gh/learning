using Microsoft.EntityFrameworkCore.Migrations;

namespace EmployeePortal.Migrations
{
    public partial class AddComputerDetails : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "ComputerDetails",
                table: "employee",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "JobType",
                table: "employee",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ComputerDetails",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "JobType",
                table: "employee");
        }
    }
}
