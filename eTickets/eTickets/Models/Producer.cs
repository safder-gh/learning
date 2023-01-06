using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    [Table("producer")]
    public class Producer : BasePerson
    {
        public List<Movie> Movies { get; set; }
    }
}
