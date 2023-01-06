using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eTickets.Models
{
    public abstract class BasePerson : BaseModel
    {
        [Display(Name = "Profile Picture")]
        [Column("profile_picture_url", Order = 1)]
        [Required(ErrorMessage = "Profile Picture is required"), DataType(DataType.Url)]
        public string ProfilePictureURL { get; set; }
        [Display(Name = "First Name")]
        [Column("first_name", Order = 2)]
        [Required(ErrorMessage = "First Name is required")]
        public string FirstName { get; set; }
        [Display(Name = "Middle Name")]
        [Column("middle_name", Order = 3)]
        public string MiddleName { get; set; }
        [Display(Name = "Last Name")]
        [Column("last_name", Order = 4)]
        public string LastName { get; set; }
        [Display(Name = "Name")]
        [NotMapped]
        public string FullName
        {
            get
            {
                return string.Format("{0} {1} {2}", this.FirstName, this.MiddleName, this.LastName);
            }
        }
        [Display(Name = "Biography")]
        [Column("bio", Order = 5)]
        [Required(ErrorMessage = "Biography is required")]
        public string Bio { get; set; }
    }
}
