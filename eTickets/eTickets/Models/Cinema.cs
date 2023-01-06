using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    [Table("cinema")]
    public class Cinema : BaseModel
    {
        [Column("logo", Order = 1)]
        [Display(Name = "Cinema Logo")]
        public string Logo { get; set; }
        [Display(Name = "Name")]
        [Column("name", Order = 2)]
        public string Name { get; set; }
        [Column("description", Order = 3)]
        [Display(Name = "Description")]
        public string Description { get; set; }
        public List<Movie> Movies { get; set; }
    }
}
