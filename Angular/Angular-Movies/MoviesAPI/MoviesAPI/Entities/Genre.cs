using MoviesAPI.Validations;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MoviesAPI.Entities
{
    [Table("genre")]
 
    public class Genre:BaseEntity
    {
        [Required(ErrorMessage ="The field with name {0} required.")]
        [StringLength(50)]
        [FirstLetterUppercase]
        public string name { get; set; }
    }
}
