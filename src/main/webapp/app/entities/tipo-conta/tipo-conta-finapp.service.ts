import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TipoContaFinapp } from './tipo-conta-finapp.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TipoContaFinappService {

    private resourceUrl = SERVER_API_URL + 'api/tipo-contas';

    constructor(private http: Http) { }

    create(tipoConta: TipoContaFinapp): Observable<TipoContaFinapp> {
        const copy = this.convert(tipoConta);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tipoConta: TipoContaFinapp): Observable<TipoContaFinapp> {
        const copy = this.convert(tipoConta);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TipoContaFinapp> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(tipoConta: TipoContaFinapp): TipoContaFinapp {
        const copy: TipoContaFinapp = Object.assign({}, tipoConta);
        return copy;
    }
}
