using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    [Table("actor")]
    public class Actor : BasePerson
    {
        public List<Actor_Movie> ActoreMovies { get; set; }
    }
}
