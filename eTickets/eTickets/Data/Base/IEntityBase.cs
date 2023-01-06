using System;

namespace eTickets.Data.Base
{
    public interface IEntityBasey
    {
        Guid Id { get; set; }
        DateTime Added_On { get; set; }
        DateTime Modified_On { get; set; }
        bool Deleted { get; set; }
    }
}
