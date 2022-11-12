using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace EmployeePortal.Migrations
{
    public partial class initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "employee",
                columns: table => new
                {
                    id = table.Column<Guid>(nullable: false),
                    deleted = table.Column<bool>(nullable: false),
                    first_name = table.Column<string>(nullable: false),
                    last_name = table.Column<string>(nullable: true),
                    jd = table.Column<string>(nullable: true),
                    employee_type = table.Column<int>(nullable: false),
                    hourly_pay = table.Column<double>(nullable: false),
                    bonus = table.Column<double>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_employee", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "employee");
        }
    }
}
