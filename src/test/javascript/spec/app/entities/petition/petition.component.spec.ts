/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PetitionComponent } from 'app/entities/petition/petition.component';
import { PetitionService } from 'app/entities/petition/petition.service';
import { Petition } from 'app/shared/model/petition.model';

describe('Component Tests', () => {
    describe('Petition Management Component', () => {
        let comp: PetitionComponent;
        let fixture: ComponentFixture<PetitionComponent>;
        let service: PetitionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PetitionComponent],
                providers: []
            })
                .overrideTemplate(PetitionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PetitionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PetitionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Petition(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.petitions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
