using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Models
{
    
    public abstract class BaseModel
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        [Column("id",Order = 0)]
        public Guid Id { set; get; }
        [Column("deleted")]
        public bool Deleted { set; get; } = true;
    }
}
