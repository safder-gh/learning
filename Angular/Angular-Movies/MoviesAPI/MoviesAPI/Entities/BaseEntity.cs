using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MoviesAPI.Entities
{
    public abstract class BaseEntity
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Column("id")]
        public int Id { get; set; }
        [Column("added_on")]
        public DateTime AddedOn { get; set; } = DateTime.Now;
        [Column("updated_on")]
        public DateTime UpdatedOn { get; set; } = DateTime.Now;
        [Column("deleted")]
        public bool Deleted { set; get; } = false;
    }
}
