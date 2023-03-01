using AspNetIdentityDemo.Shared;
using AspNetIdentyDemo.Api.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AspNetIdentyDemo.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IUserService userService;

        public AuthController(IUserService userService)
        {
            this.userService = userService;
        }
        [HttpPost("Register")]
        public async Task<IActionResult> RegisterAsync([FromBody] RegisterViewModel model)
        {
            
                var result = await userService.RegisterUserAsync(model);
                if (result.IsSuccess) return Ok(result);
                return BadRequest(result);


        }
        [HttpPost("Login")]
        public async Task<IActionResult> LoginAsync([FromBody] LoginViewModel model)
        {
            var result = await userService.LoginUserAsync(model);
            if (result.IsSuccess) return Ok(result);
            return BadRequest(result);
        }

    }
}
