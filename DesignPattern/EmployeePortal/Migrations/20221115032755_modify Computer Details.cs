using Microsoft.EntityFrameworkCore.Migrations;

namespace EmployeePortal.Migrations
{
    public partial class modifyComputerDetails : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "JobType",
                table: "employee",
                newName: "job_type");

            migrationBuilder.RenameColumn(
                name: "ComputerDetails",
                table: "employee",
                newName: "computer_details");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "job_type",
                table: "employee",
                newName: "JobType");

            migrationBuilder.RenameColumn(
                name: "computer_details",
                table: "employee",
                newName: "ComputerDetails");
        }
    }
}
