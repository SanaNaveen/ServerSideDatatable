import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn:'root'
})
export class UserDataService {

    constructor(private http : HttpClient){}

    baseUrl = environment.baseUrl;

    getUserData(req : any) : Observable<any>{
        return this.http.post(this.baseUrl+'/getAllUserData',req);
    }

    searchData(req : any) : Observable<any>{
        return this.http.post(this.baseUrl + "/searchData",req);
    }
}