using eTickets.Data.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    [Table("movie")]
    public class Movie : BaseModel
    {
        [Column("name", Order = 1)]
        [Display(Name = "Name")]
        public string Name { get; set; }
        [Column("description", Order = 2)]
        [Display(Name = "Description")]
        public string Description { get; set; }
        [Column("price", Order = 3)]
        [Display(Name = "Price")]
        public double Price { get; set; }
        [Column("image_url", Order = 4)]
        [Display(Name = "Image URL")]
        public string ImageURL { get; set; }
        [Column("start_date", Order = 5)]
        [Display(Name = "Start Date")]
        public DateTime StartDate { get; set; }
        [Column("end_date", Order = 6)]
        [Display(Name = "End Date")]
        public DateTime EndDate { get; set; }
        [Column("movie_category", Order = 7)]
        [Display(Name = "Category")]
        public MovieCategory MovieCategory { get; set; }
        // One to Many
        public List<Actor_Movie> ActoreMovies { get; set; }
        //Cinema One to One
        [Column("cinema_id", Order = 8)]
        [Display(Name = "Cinema")]
        public Guid CinemaId { get; set; }
        [ForeignKey("CinemaId")]
        public Cinema Cinema { get; set; }
        //Producer One to One
        [Column("producer_id", Order = 8)]
        [Display(Name = "Producer")]
        public Guid ProducerId { get; set; }
        [ForeignKey("ProducerId")]
        public Producer Producer { get; set; }
        [NotMapped]
        public string Status
        {
            get
            {
                string returnVal = "UPCOMING";
                if (DateTime.Now >= this.StartDate && DateTime.Now <= this.EndDate)
                {
                    returnVal = "AVAILABLE";
                }
                else if (DateTime.Now > this.EndDate)
                {
                    returnVal = "EXPIRED";
                }

                return returnVal;
            }
        }


    }
}
