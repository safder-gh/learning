using eTickets.Data.Base;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    public abstract class BaseModel : IEntityBasey
    {
        [Key]
        [Column("id", Order = 0)]
        public Guid Id { get; set; } = new Guid();
        [Column("added_on")]
        public DateTime Added_On { get; set; } = DateTime.Now;
        [Column("modified_on")]
        public DateTime Modified_On { get; set; } = DateTime.Now;
        [Column("deleted")]
        public bool Deleted { get; set; } = false;

    }
}
