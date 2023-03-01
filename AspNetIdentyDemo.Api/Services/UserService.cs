using AspNetIdentityDemo.Shared;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using System.Text;

namespace AspNetIdentyDemo.Api.Services
{
    public class UserService : IUserService
    {
        private readonly UserManager<IdentityUser> userManager;
        private readonly IConfiguration configuration;

        public UserService(UserManager<IdentityUser> userManager,IConfiguration configuration)
        {
            this.userManager = userManager;
            this.configuration = configuration;
        }

        public async Task<UserManagerResponse> LoginUserAsync(LoginViewModel model)
        {
            var user = await userManager.FindByEmailAsync(model.Email);
            if(user == null)
            {
                return new UserManagerResponse
                {
                    IsSuccess = false,
                    Message = "There is no user with that email.",
                };
            }
            var result = await userManager.CheckPasswordAsync(user, model.Password);
            if (!result)
            {
                return new UserManagerResponse
                {
                    IsSuccess = false,
                    Message = "Invalid Password.",
                };
            }

            var claims = new []{
                new Claim("Email",model.Email),
                    new Claim(ClaimTypes.NameIdentifier,user.Id)

            };

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(configuration["AuthSetting:Key"]));
            var token = new JwtSecurityToken(
                issuer: configuration["AuthSetting:Issuer"],
                audience: configuration["AuthSetting:Audience"],
                expires: DateTime.Now.AddDays(30),
                claims: claims,
                signingCredentials:new SigningCredentials(key,SecurityAlgorithms.HmacSha256)
                );
            var tokenAsString = new JwtSecurityTokenHandler().WriteToken(token);

            return new UserManagerResponse
            {
                IsSuccess = true,
                Message = tokenAsString,
                ExpiryDate = token.ValidTo
            };

        }

        public async Task<UserManagerResponse> RegisterUserAsync(RegisterViewModel model)
        {
            if (model == null) throw new NullReferenceException("Register Model Is Null");
            if (model.Password != model.ConfirmPassword) return new UserManagerResponse { 
                Message ="Password and confirm password doesn't match",
                IsSuccess = false,
            };
            var identityUser = new IdentityUser { 
                Email = model.Email,
             UserName = model.Email,
            };

            var result = await userManager.CreateAsync(identityUser, model.Password);
            if (result.Succeeded)
            {
                return new UserManagerResponse
                {
                    Message = "User created successfully.",
                    IsSuccess = true,
                };
            }
            return new UserManagerResponse
            {
                Message = "User can't be created.",
                IsSuccess = false,
                Errors = result.Errors.Select(e => e.Description)
            };
        }
    }
}
