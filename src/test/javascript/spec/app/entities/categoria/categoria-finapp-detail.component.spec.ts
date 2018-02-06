/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FinapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CategoriaFinappDetailComponent } from '../../../../../../main/webapp/app/entities/categoria/categoria-finapp-detail.component';
import { CategoriaFinappService } from '../../../../../../main/webapp/app/entities/categoria/categoria-finapp.service';
import { CategoriaFinapp } from '../../../../../../main/webapp/app/entities/categoria/categoria-finapp.model';

describe('Component Tests', () => {

    describe('CategoriaFinapp Management Detail Component', () => {
        let comp: CategoriaFinappDetailComponent;
        let fixture: ComponentFixture<CategoriaFinappDetailComponent>;
        let service: CategoriaFinappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinapplicationTestModule],
                declarations: [CategoriaFinappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CategoriaFinappService,
                    JhiEventManager
                ]
            }).overrideTemplate(CategoriaFinappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategoriaFinappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaFinappService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CategoriaFinapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.categoria).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
