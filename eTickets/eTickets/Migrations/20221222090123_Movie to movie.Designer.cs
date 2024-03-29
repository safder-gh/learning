﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using eTickets.Data;

namespace eTickets.Migrations
{
    [DbContext(typeof(AppDbContext))]
    [Migration("20221222090123_Movie to movie")]
    partial class Movietomovie
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.1.32")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("eTickets.Models.Actor", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("Added_On")
                        .HasColumnName("added_on")
                        .HasColumnType("datetime2");

                    b.Property<string>("Bio")
                        .HasColumnName("bio")
                        .HasColumnType("nvarchar(max)");

                    b.Property<bool>("Deleted")
                        .HasColumnName("deleted")
                        .HasColumnType("bit");

                    b.Property<string>("FisrtName")
                        .HasColumnName("first_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("LastName")
                        .HasColumnName("last_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("MiddleName")
                        .HasColumnName("middle_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("Modified_On")
                        .HasColumnName("modified_on")
                        .HasColumnType("datetime2");

                    b.Property<string>("ProfilePictureURL")
                        .HasColumnName("profile_picture_url")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.ToTable("actor");
                });

            modelBuilder.Entity("eTickets.Models.Actor_Movie", b =>
                {
                    b.Property<Guid>("ActorId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("MovieId")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("ActorId", "MovieId");

                    b.HasIndex("MovieId");

                    b.ToTable("actor_movie");
                });

            modelBuilder.Entity("eTickets.Models.Cinema", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("Added_On")
                        .HasColumnName("added_on")
                        .HasColumnType("datetime2");

                    b.Property<bool>("Deleted")
                        .HasColumnName("deleted")
                        .HasColumnType("bit");

                    b.Property<string>("Description")
                        .HasColumnName("description")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Logo")
                        .HasColumnName("logo")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("Modified_On")
                        .HasColumnName("modified_on")
                        .HasColumnType("datetime2");

                    b.Property<string>("Name")
                        .HasColumnName("name")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.ToTable("cinema");
                });

            modelBuilder.Entity("eTickets.Models.Movie", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("Added_On")
                        .HasColumnName("added_on")
                        .HasColumnType("datetime2");

                    b.Property<Guid>("CinemaId")
                        .HasColumnName("cinema_id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<bool>("Deleted")
                        .HasColumnName("deleted")
                        .HasColumnType("bit");

                    b.Property<string>("Description")
                        .HasColumnName("description")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("EndDate")
                        .HasColumnName("end_date")
                        .HasColumnType("datetime2");

                    b.Property<string>("ImageURL")
                        .HasColumnName("image_url")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("Modified_On")
                        .HasColumnName("modified_on")
                        .HasColumnType("datetime2");

                    b.Property<int>("MovieCategory")
                        .HasColumnName("movie_category")
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .HasColumnName("name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<double>("Price")
                        .HasColumnName("price")
                        .HasColumnType("float");

                    b.Property<Guid>("ProducerId")
                        .HasColumnName("producer_id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("StartDate")
                        .HasColumnName("start_date")
                        .HasColumnType("datetime2");

                    b.Property<Guid?>("producer_id")
                        .HasColumnName("producer_id1")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("Id");

                    b.HasIndex("CinemaId");

                    b.HasIndex("producer_id");

                    b.ToTable("movie");
                });

            modelBuilder.Entity("eTickets.Models.Producer", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("id")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("Added_On")
                        .HasColumnName("added_on")
                        .HasColumnType("datetime2");

                    b.Property<string>("Bio")
                        .HasColumnName("bio")
                        .HasColumnType("nvarchar(max)");

                    b.Property<bool>("Deleted")
                        .HasColumnName("deleted")
                        .HasColumnType("bit");

                    b.Property<string>("FisrtName")
                        .HasColumnName("first_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("LastName")
                        .HasColumnName("last_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("MiddleName")
                        .HasColumnName("middle_name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("Modified_On")
                        .HasColumnName("modified_on")
                        .HasColumnType("datetime2");

                    b.Property<string>("ProfilePictureURL")
                        .HasColumnName("profile_picture_url")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Id");

                    b.ToTable("producer");
                });

            modelBuilder.Entity("eTickets.Models.Actor_Movie", b =>
                {
                    b.HasOne("eTickets.Models.Actor", "Actor")
                        .WithMany("ActoreMovies")
                        .HasForeignKey("ActorId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("eTickets.Models.Movie", "Movie")
                        .WithMany("ActoreMovies")
                        .HasForeignKey("MovieId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("eTickets.Models.Movie", b =>
                {
                    b.HasOne("eTickets.Models.Cinema", "Cinema")
                        .WithMany("Movies")
                        .HasForeignKey("CinemaId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("eTickets.Models.Producer", "Producer")
                        .WithMany("Movies")
                        .HasForeignKey("producer_id");
                });
#pragma warning restore 612, 618
        }
    }
}
