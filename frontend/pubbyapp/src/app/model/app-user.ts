export class AppUser {
	id:number = 0;
    username:string;
    password:string;
    roles?: string[];
    authdata?: string;
    
    constructor(username:string, password:string){
    	this.username = username;
    	this.password = password;
    }
}
