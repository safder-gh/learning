using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    [Table("actor_movie")]
    public class Actor_Movie
    {
        public Guid MovieId { get; set; }
        public Movie Movie { get; set; }
        public Guid ActorId { get; set; }
        public Actor Actor { get; set; }
    }
}
