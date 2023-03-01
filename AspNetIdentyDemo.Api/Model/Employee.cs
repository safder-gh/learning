using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AspNetIdentyDemo.Api.Model
{
    [Table("employee")]
    public class Employee
    {
        public Employee()
        {

        }
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public Guid id { get; set; }
        [Column("first_name")]
        public string FirstName { get; set; }
        [Column("last_name")]
        public string LastName { get; set; }
        [Column("email_name")]
        public string Email { get; set; }
        [Column("phone")]
        public string Phone { get; set; }
    }
}
