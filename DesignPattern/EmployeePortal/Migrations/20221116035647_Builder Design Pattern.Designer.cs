﻿// <auto-generated />
using System;
using EmployeePortal.DAL;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace EmployeePortal.Migrations
{
    [DbContext(typeof(DesignPatternContext))]
    [Migration("20221116035647_Builder Design Pattern")]
    partial class BuilderDesignPattern
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.1.31")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("EmployeePortal.Models.Employee", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<double>("Bonus")
                        .HasColumnName("bonus")
                        .HasColumnType("float");

                    b.Property<string>("ComputerDetails")
                        .HasColumnName("computer_details")
                        .HasColumnType("nvarchar(max)");

                    b.Property<bool>("Deleted")
                        .HasColumnName("deleted")
                        .HasColumnType("bit");

                    b.Property<int>("EmployeeType")
                        .HasColumnName("employee_type")
                        .HasColumnType("int");

                    b.Property<string>("FirstName")
                        .IsRequired()
                        .HasColumnName("first_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<double>("HourlyPay")
                        .HasColumnName("hourly_pay")
                        .HasColumnType("float");

                    b.Property<double>("HouseAllowance")
                        .HasColumnName("house_allowance")
                        .HasColumnType("float");

                    b.Property<string>("JD")
                        .HasColumnName("jd")
                        .HasColumnType("nvarchar(max)");

                    b.Property<int>("JobType")
                        .HasColumnName("job_type")
                        .HasColumnType("int");

                    b.Property<string>("LastName")
                        .HasColumnName("last_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<double>("MedicalAllowance")
                        .HasColumnName("medical_allowance")
                        .HasColumnType("float");

                    b.Property<string>("SystemConfigurationDetails")
                        .HasColumnName("system_configuration_details")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.ToTable("employee");
                });
#pragma warning restore 612, 618
        }
    }
}
