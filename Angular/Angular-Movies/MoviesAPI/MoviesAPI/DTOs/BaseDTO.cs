using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MoviesAPI.DTOs
{
    public abstract class BaseDTO
    {
        public int Id { get; set; }
        public DateTime AddedOn { get; set; } = DateTime.Now;
        public DateTime UpdatedOn { get; set; } = DateTime.Now;
        public bool Deleted { set; get; } = false;
    }
}
